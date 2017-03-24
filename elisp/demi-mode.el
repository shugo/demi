;;;  demi-mode.el

;; Adapted from ruby code editing commands 'ruby-mode.el', by 
;; Yukihiro Matsumoto.

;; Setup:
;;
;;   append these lines to your .emacs:
;;
;;   (autoload 'demi-mode "demi-mode" "Demi Editing Mode" t)
;;   (setq auto-mode-alist
;;         (append '(("\\.dm$" . demi-mode)) auto-mode-alist))

(defconst demi-mode-version "0.1")

(defvar demi-indent-level 4
  "*Indentation of demi statements.")

(defvar demi-continued-expression-offset 2
  "*Extra indent for continued expression lines.")

(defvar demi-mode-abbrev-table nil
  "Abbrev table in use in demi-mode buffers.")

(define-abbrev-table 'demi-mode-abbrev-table ())

(defvar demi-mode-map nil "Keymap used in Demi mode.")

(if demi-mode-map
    nil
  (setq demi-mode-map (make-sparse-keymap))
  (define-key demi-mode-map "{" 'demi-electric-brace)
  (define-key demi-mode-map "}" 'demi-electric-brace)
  (define-key demi-mode-map "[" 'demi-electric-brace)
  (define-key demi-mode-map "]" 'demi-electric-brace)
  (define-key demi-mode-map ":" 'demi-electric-brace)
  (define-key demi-mode-map ";" 'demi-electric-brace)
  (define-key demi-mode-map "\e\C-a" 'demi-beginning-of-defun)
  (define-key demi-mode-map "\e\C-e" 'demi-end-of-defun)
  (define-key demi-mode-map "\e\C-b" 'demi-beginning-of-block)
  (define-key demi-mode-map "\e\C-f" 'demi-end-of-block)
  (define-key demi-mode-map "\e\C-p" 'demi-beginning-of-block)
  (define-key demi-mode-map "\e\C-n" 'demi-end-of-block)
  (define-key demi-mode-map "\t" 'demi-indent-command)
  (define-key demi-mode-map "\C-c\C-e" 'demi-insert-end)
  (define-key demi-mode-map "\C-j" 'demi-reindent-then-newline-and-indent)
  (define-key demi-mode-map "\C-m" 'newline))

(defvar demi-mode-syntax-table nil
  "Syntax table in use in demi-mode buffers.")

(if demi-mode-syntax-table
    ()
  (setq demi-mode-syntax-table (make-syntax-table))
  (modify-syntax-entry ?\\ "\\" demi-mode-syntax-table)
  (modify-syntax-entry ?+ "." demi-mode-syntax-table)
  (modify-syntax-entry ?- "." demi-mode-syntax-table)
  (modify-syntax-entry ?= "." demi-mode-syntax-table)
  (modify-syntax-entry ?% "." demi-mode-syntax-table)
  (modify-syntax-entry ?< "." demi-mode-syntax-table)
  (modify-syntax-entry ?> "." demi-mode-syntax-table)
  (modify-syntax-entry ?& "." demi-mode-syntax-table)
  (modify-syntax-entry ?| "." demi-mode-syntax-table)
  (modify-syntax-entry ?\" "\"" demi-mode-syntax-table)
  (modify-syntax-entry ?\' "\"" demi-mode-syntax-table)
  (modify-syntax-entry ?\( "()" demi-mode-syntax-table)
  (modify-syntax-entry ?\) ")(" demi-mode-syntax-table)
  (modify-syntax-entry ?{ "(}" demi-mode-syntax-table)
  (modify-syntax-entry ?} "){" demi-mode-syntax-table)
  (modify-syntax-entry ?\[ "(]" demi-mode-syntax-table)
  (modify-syntax-entry ?\] ")[" demi-mode-syntax-table)
  (cond
   ((string-match "XEmacs" emacs-version)
    (modify-syntax-entry ?/ ". 1456" demi-mode-syntax-table)
    (modify-syntax-entry ?* ". 23" demi-mode-syntax-table))
   (t
    (modify-syntax-entry ?/ ". 124b" demi-mode-syntax-table)
    (modify-syntax-entry ?* ". 23" demi-mode-syntax-table)))
  (modify-syntax-entry ?\n "> b" demi-mode-syntax-table))

(defconst demi-font-lock-keywords
  (list
   ;; reserved words
   (cons (concat
	  "\\(^\\|[^_\\.:]\\)\\<\\("
	  (mapconcat
	   'identity
	   '("break" "catch" "class" "continue" "def" "else"
	     "elsif" "end" "exception" "finally" "for" "if" "in"
	     "import" "instanceof" "module" "new" "return"
	     "synchronized" "this" "throw" "try" "while")
	   "\\|")
	  "\\)\\>")
	 2)
   (cons (concat "\\<\\("
		 (regexp-opt '("boolean" "char" "byte" "short" "int" "long"
			       "float" "double" "void"))
		 "\\)\\>")
	 'font-lock-type-face)
   '("\\<\\(false\\|null\\|true\\)\\>" . font-lock-reference-face)
   '("\\<\\(def\\)\\>[ \t]*\\(\\sw+\\)?"
     (1 font-lock-keyword-face) (2 font-lock-function-name-face nil t))
   '("\\<\\(module\\)\\>[ \t]*\\(\\sw+\\)?"
     (1 font-lock-keyword-face) (2 font-lock-type-face nil t))
   (list "\\<\\(instanceof\\|new\\)\\>[ \t]*\\(\\sw+\\)?"
	 '(1 font-lock-keyword-face) '(2 font-lock-type-face nil t)
	 '("\\=[ \t]*,[ \t]*\\(\\sw+\\)"
	   (if (match-beginning 2) (goto-char (match-end 2))) nil
	   (1 font-lock-type-face)))
   '("\\<\\(class\\)\\>[ \t]*(\\(.*\\))"
     (1 font-lock-keyword-face) (2 font-lock-type-face))
   '("\\<\\(import\\)\\>[ \t]*\\([^ \t\r\n;]+\\)?"
     (1 font-lock-keyword-face) (2 font-lock-reference-face nil t)))
  "Expressions to highlight in Demi mode.")

(defconst demi-block-beg-re
  "module\\|def\\|if\\|while\\|for\\|try\\|synchronized")

(defconst demi-indent-beg-re
  "\\(\\s *\\(module\\|def\\)\\)\\|if\\|while\\|for\\|try\\|synchronized")

(defconst demi-block-mid-re
  "else\\|elsif\\|catch\\|finally")

(defconst demi-block-end-re "end")

(defconst demi-delimiter
  (concat "[(){}\"']\\|//\\|/\\*\\|\\[\\|\\]\\|\\<\\("
	  demi-block-beg-re
	  "\\|" demi-block-end-re
	  "\\)\\>"))

(defconst demi-negative
  (concat "^[ \t]*\\(\\(" demi-block-mid-re "\\)\\>\\|\\("
	    demi-block-end-re "\\)\\>\\|\\}\\|\\]\\)"))

(defconst demi-operator-chars "[,.+*/%-&|^~=<>]")
(defconst demi-symbol-chars "[a-zA-Z0-9_]")

(defun demi-mode ()
  "Major mode for editing Demi scripts.
\\[demi-indent-command] properly indents subexpressions of multi-line
class, module, def, if, while, for, do, and case statements, taking
nesting into account.

The variable demi-indent-level controls the amount of indentation.
\\{demi-mode-map}"
  (interactive)
  (kill-all-local-variables)
  (use-local-map demi-mode-map)
  (setq mode-name "Demi")
  (setq major-mode 'demi-mode)
  (set-syntax-table demi-mode-syntax-table)
  (setq local-abbrev-table demi-mode-abbrev-table)
  (make-local-variable 'indent-line-function)
  (setq indent-line-function 'demi-indent-line)
  (make-local-variable 'require-final-newline)
  (setq require-final-newline t)
  (make-variable-buffer-local 'comment-start)
  (setq comment-start "// ")
  (make-variable-buffer-local 'comment-end)
  (setq comment-end "")
  (make-variable-buffer-local 'comment-column)
  (setq comment-column 32)
  (make-variable-buffer-local 'comment-start-skip)
  (setq comment-start-skip "//+[ \t]*")
  (make-local-variable 'parse-sexp-ignore-comments)
  (setq parse-sexp-ignore-comments t)
  (make-local-variable 'font-lock-defaults)
  (setq font-lock-defaults '((demi-font-lock-keywords)
			     nil nil ((?\_ . "w"))))
  (run-hooks 'demi-mode-hook))

(defun demi-current-indentation ()
  (save-excursion
    (beginning-of-line)
    (back-to-indentation)
    (current-column)))

(defun demi-indent-line (&optional flag)
  "Correct indentation of the current demi line."
  (demi-indent-to (demi-calculate-indent)))

(defun demi-indent-command ()
  (interactive)
  (demi-indent-line t))

(defun demi-indent-to (x)
  (if x
      (let (shift top beg)
	(and (< x 0)
	     (error "invalid nest"))
	(setq shift (current-column))
	(beginning-of-line)
	(setq beg (point))
	(back-to-indentation)
	(setq top (current-column))
	(skip-chars-backward " \t")
	(cond
	 ((>= x shift)
	  (setq shift 0))
	 ((>= shift top)
	  (setq shift (- shift top)))
	 (t (setq shift 0)))
	(if (and (bolp)
		 (= x top))
	    (move-to-column (+ x shift))
	  (move-to-column top)
	  (delete-region beg (point))
	  (beginning-of-line)
	  (indent-to x)
	  (move-to-column (+ x shift))))))

(defun demi-expr-beg (&optional modifier)
  (save-excursion
    (if (looking-at "\\?")
	(progn
	  (or (bolp) (forward-char -1))
	  (not (looking-at "\\sw")))
      (skip-chars-backward " \t")
      (or (bolp) (forward-char -1))
      (or (looking-at demi-operator-chars)
	  (looking-at "[\\[({!?]")
	  (bolp)
	  (and (looking-at demi-symbol-chars)
	       (forward-word -1)
	       (or 
		(and modifier (bolp))
		(looking-at demi-block-beg-re)
		;(looking-at demi-block-op-re)
		(looking-at demi-block-mid-re)
		(and modifier
		     (save-excursion
		       (forward-char -1)
		       (let ((c (char-after (point))))
			 (or (eq c ?.)
			     (eq c ? )
			     (eq c ?\t))))))
	       (goto-char (match-end 0))
	       (looking-at "[^_]"))))))

(defun demi-parse-region (start end)
  (let ((indent-point end)
	  (indent 0)
	  (in-string nil)
	  (in-paren nil)
	  (depth 0)
	  (nest nil)
	  (pcol nil))
    (save-excursion
	(if start
	    (goto-char start)
	  (demi-beginning-of-indent))
	(save-restriction
	  (narrow-to-region (point) end)
	  (while (and (> indent-point (point))
		      (re-search-forward demi-delimiter indent-point t))
	    (let ((pnt (point)) w)
	      (goto-char (match-beginning 0))
	      (cond
	       ((or (looking-at "\"")	;skip string
		    (looking-at "'"))
		(setq w (char-after (point)))
		(cond
		 ((and (not (eobp))
		       (re-search-forward (format "[^\\]%c" w)
					  indent-point t))
		  nil)
		 (t
		  (setq in-string (point))
		  (goto-char indent-point))))
	       ((looking-at "/\\*")
		(cond
		 ((and (not (eobp))
		       (re-search-forward "[^\\]\\*/" indent-point t))
		  nil)
		 (t
		  (setq in-string (point))
		  (goto-char indent-point))))
	       ((looking-at "//")		;skip comment
		(forward-line 1)
		(goto-char (point))
		)
	       ((looking-at "(")
		(setq nest (cons (cons (char-after (point)) pnt) nest))
		(setq pcol (cons (cons pnt depth) pcol))
		(setq depth 0)
		(goto-char pnt)
		)
	       ((looking-at "[\\[{]")
		(setq nest (cons (cons (char-after (point)) pnt) nest))
		(setq depth (1+ depth))
		(goto-char pnt)
		)
	       ((looking-at ")")
		(setq nest (cdr nest))
		(setq depth (cdr (car pcol)))
		(setq pcol (cdr pcol))
		(goto-char pnt))
	       ((looking-at "[])}]")
		(setq nest (cdr nest))
		(setq depth (1- depth))
		(goto-char pnt))
	       ((looking-at demi-block-end-re)
		(if (or (and (not (bolp))
			     (progn
			       (forward-char -1)
			       (eq ?_ (char-after (point)))))
			(progn
			  (goto-char pnt)
			  (setq w (char-after (point)))
			  (or (eq ?_ w)
			      (eq ?! w)
			      (eq ?? w))))
		    nil
		  (setq nest (cdr nest))
		  (setq depth (1- depth)))
		(goto-char pnt))
	       ((looking-at demi-block-beg-re)
		(and 
		 (or (bolp)
		     (progn
		       (forward-char -1)
		       (not (eq ?_ (char-after (point))))))
		 (progn
		   (goto-char pnt)
		   (setq w (char-after (point)))
		   (and (not (eq ?_ w))
			(not (eq ?! w))
			(not (eq ?? w))))
		 (progn
		   (goto-char (match-beginning 0))
		   t)
		 (progn
		   (setq nest (cons (cons nil pnt) nest))
		   (setq depth (1+ depth))))
		(if (looking-at "def\\s *[/`]")
		    (goto-char (match-end 0))
		  (goto-char pnt)))
	       (t
		(error (format "bad string %s"
			       (buffer-substring (point) pnt)
			       )))))))
	(list in-string (car nest) depth (car (car pcol))))))

(defun demi-calculate-indent (&optional parse-start)
  (save-excursion
    (beginning-of-line)
    (let ((indent-point (point))
	    (case-fold-search nil)
	    state bol eol
	    (indent 0))
	(if parse-start
	    (goto-char parse-start)
	  (demi-beginning-of-indent)
	  (setq parse-start (point)))
	(back-to-indentation)
	(setq indent (current-column))
	(setq state (demi-parse-region parse-start indent-point))
	(cond
	 ((nth 0 state)			; within string
	  (setq indent nil))		;  do nothing

	 ((car (nth 1 state))		; in paren
	  (goto-char (cdr (nth 1 state)))
	  (cond
	   ((eq (car (nth 1 state)) ?\( )
	    (let ((column (current-column))
		  (s (demi-parse-region (point) indent-point)))
	      (cond
	       ((and (nth 2 s) (> (nth 2 s) 0))
		(goto-char (cdr (nth 1 s)))
		(forward-word -1)
		(setq indent (+ (current-column) demi-indent-level)))
	       (t 
		(setq indent (current-column))))))
	   ((memq (car (nth 1 state)) '(?\[ ?{))
	    (goto-char (cdr (nth 1 state)))
	    (while
		(and (back-to-indentation)
		     (save-excursion
		       (demi-backward-to-noncomment parse-start)
		       (and
			(> (point) parse-start)
			(not (memq (preceding-char) '(?: ?\; ?\[ ?{))))))
	      (forward-line -1))
	    (setq indent (+ (current-column) demi-indent-level))
	    (goto-char indent-point)
	    (demi-backward-to-noncomment parse-start)
	    (and (> (point) parse-start)
		 (not (memq (preceding-char) '(?: ?\; ?\[ ?{)))
		 (goto-char indent-point)
		 (not (looking-at demi-negative))
		 (setq indent
		       (+ indent
			  demi-continued-expression-offset))))
	   (t
	    (cond
	     ((nth 3 state)
	      (goto-char (nth 3 state))
	      (setq indent (+ (current-column) demi-indent-level)))
	     (t
	      (goto-char parse-start)
	      (back-to-indentation)
	      (setq indent (+ (current-column)
			      (* (nth 2 state) demi-indent-level))))))))

	 ((and (nth 2 state)(> (nth 2 state) 0)) ; in nest
	  (goto-char (cdr (nth 1 state)))
	  (forward-word -1)		; skip back a keyword
	  (setq indent (+ (current-column) demi-indent-level)))

	 ((and (nth 2 state) (< (nth 2 state) 0)) ; in negative nest
	  (setq indent (+ (current-column)
			  (* (nth 2 state) demi-indent-level)))))

	(cond
	 (indent
	  (goto-char indent-point)
	  (end-of-line)
	  (setq eol (point))
	  (beginning-of-line)
	  (cond 
	   ((re-search-forward demi-negative eol t)
	    (and (not (eq ?_ (char-after (match-end 0))))
		 (setq indent (- indent demi-indent-level))))
	   ;;operator terminated lines
	   ((and
	     (save-excursion
	       (beginning-of-line)
	       (not (bobp)))
	     (or (null (car (nth 1 state))) ;not in parens
		 (and (eq (car (nth 1 state)) ?{)
		      (save-excursion	;except non-block braces
			(goto-char (cdr (nth 1 state)))
			(or (bobp) (forward-char -1))
			(not (demi-expr-beg))))))
	    ;; goto beginning of non-empty no-comment line
	    (let (end done)
	      (while (not done)
		(skip-chars-backward " \t\n")
		(setq end (point))
		(beginning-of-line)
		(if (re-search-forward "^\\s *//" end t)
		    (beginning-of-line)
		  (setq done t))))
	    (setq bol (point))
	    (end-of-line)
	    (skip-chars-backward " \t")
	    (demi-backward-to-noncomment parse-start)
	    (and (> (point) parse-start)
		 (not (memq (preceding-char) '(?: ?\;)))
		 (goto-char indent-point)
		 (not (looking-at demi-negative))
		 (setq indent
		       (+ indent
			  demi-continued-expression-offset)))))))
	indent)))
 
(defun demi-backward-to-noncomment (lim)
  (let (opoint stop)
    (while (not stop)
      (skip-chars-backward " \t\n\r\f" lim)
      (setq opoint (point))
      (cond ((and (>= (point) (+ 2 lim))
		  (save-excursion
		    (forward-char -2)
		    (looking-at "\\*/")))
	     (search-backward "/*" lim 'move))
	    ((search-backward "//"
			      (max (save-excursion
				     (beginning-of-line)
				     (point))
				   lim)
			      'move))
	    ;; No comment to be found.
	    (t (beginning-of-line)
	       (skip-chars-forward " \t")
	       (setq stop t)
	       (goto-char opoint))))))

(defun demi-electric-brace (arg)
  (interactive "P")
  (self-insert-command (prefix-numeric-value arg))
  (demi-indent-line t))

(defun demi-beginning-of-defun (&optional arg)
  "Move backward to next beginning-of-defun.
With argument, do this that many times.
Returns t unless search stops due to end of buffer."
  (interactive "p")
  (and (re-search-backward (concat "^\\(" demi-block-beg-re "\\)\\b")
			   nil 'move (or arg 1))
       (progn (beginning-of-line) t)))

(defun demi-beginning-of-indent ()
  (and (re-search-backward (concat "^\\(" demi-indent-beg-re "\\)\\b")
			   nil 'move)
       (progn
	 (beginning-of-line)
	 t)))

(defun demi-end-of-defun (&optional arg)
  "Move forward to next end of defun.
An end of a defun is found by moving forward from the beginning of one."
  (interactive "p")
  (and (re-search-forward (concat "^\\(" demi-block-end-re "\\)\\b[^_]")
			  nil 'move (or arg 1))
       (progn (beginning-of-line) t))
  (forward-line 1))

(defun demi-move-to-block (n)
  (let (start pos done down)
    (setq start (demi-calculate-indent))
    (if (eobp)
	nil
      (while (and (not (bobp)) (not done))
	(forward-line n)
	(cond
	 ((looking-at "^$"))
	 ((looking-at "^\\s *//"))
	 (t
	  (setq pos (current-indentation))
	  (cond
	   ((< start pos)
	    (setq down t))
	   ((and down (= pos start))
	    (setq done t))
	   ((> start pos)
	    (setq done t)))))
	(if done
	    (progn
	      (back-to-indentation)
	      (if (looking-at demi-block-mid-re)
		  (setq done nil)))))))
  (back-to-indentation))

(defun demi-beginning-of-block ()
  "Move backward to next beginning-of-block"
  (interactive)
  (demi-move-to-block -1))

(defun demi-end-of-block ()
  "Move forward to next beginning-of-block"
  (interactive)
  (demi-move-to-block 1))

(defun demi-reindent-then-newline-and-indent ()
  (interactive "*")
  (save-excursion
    (delete-region (point) (progn (skip-chars-backward " \t") (point))))
  (newline)
  (save-excursion
    (forward-line -1)
    (indent-according-to-mode))
  (indent-according-to-mode))

(fset 'demi-encomment-region (symbol-function 'comment-region))

(defun demi-decomment-region (beg end)
  (interactive "r")
  (save-excursion
    (goto-char beg)
    (while (re-search-forward "^\\([ \t]*\\)//" end t)
      (replace-match "\\1" nil nil)
      (save-excursion
	(demi-indent-line)))))

(defun demi-insert-end ()
  (interactive)
  (insert "end")
  (demi-indent-line t)
  (end-of-line))

(provide 'demi-mode)

;;; demi-mode.el ends here.
