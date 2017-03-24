VERSION	= 0_1_6

JAVAC	= javac
JAVACC	= javacc
JJTREE	= jjtree

PKGDIR	= ../../../../../../..

SRCS	= ASCII_UCodeESC_CharStream.java \
	  ASTAddAssign.java \
	  ASTAddNode.java \
	  ASTAllocationExpression.java \
	  ASTAndAssign.java \
	  ASTAndNode.java \
	  ASTArgumentList.java \
	  ASTArrayAccess.java \
	  ASTArrayExpression.java \
	  ASTAssignment.java \
	  ASTBitwiseAndNode.java \
	  ASTBitwiseComplNode.java \
	  ASTBitwiseOrNode.java \
	  ASTBitwiseXorNode.java \
	  ASTBooleanLiteral.java \
	  ASTBreak.java \
	  ASTCharacterLiteral.java \
	  ASTClassExpression.java \
	  ASTClosure.java \
	  ASTContinue.java \
	  ASTDivAssign.java \
	  ASTDivNode.java \
	  ASTEqualNode.java \
	  ASTException.java \
	  ASTExpressionList.java \
	  ASTFloatLiteral.java \
	  ASTForExpression.java \
	  ASTFunctionCall.java \
	  ASTFunctionDefinition.java \
	  ASTGlobalVariable.java \
	  ASTGreaterEqualNode.java \
	  ASTGreaterThanNode.java \
	  ASTHashExpression.java \
	  ASTIdentityEqualNode.java \
	  ASTIfExpression.java \
	  ASTImport.java \
	  ASTInNode.java \
	  ASTInstanceOfNode.java \
	  ASTIntegerLiteral.java \
	  ASTLShiftAssign.java \
	  ASTLShiftNode.java \
	  ASTLessEqualNode.java \
	  ASTLessThanNode.java \
	  ASTMemberAccess.java \
	  ASTModAssign.java \
	  ASTModNode.java \
	  ASTModuleDefinition.java \
	  ASTMulAssign.java \
	  ASTMulNode.java \
	  ASTNotEqualNode.java \
	  ASTNotNode.java \
	  ASTNullLiteral.java \
	  ASTOrAssign.java \
	  ASTOrNode.java \
	  ASTPostDecNode.java \
	  ASTPostIncNode.java \
	  ASTPrimarySuffix.java \
	  ASTPreDecNode.java \
	  ASTPreIncNode.java \
	  ASTRShiftAssign.java \
	  ASTRSignedShiftNode.java \
	  ASTRUShiftAssign.java \
	  ASTRUnsignedShiftNode.java \
	  ASTRangeNode.java \
	  ASTReturn.java \
	  ASTStaticMemberAccess.java \
	  ASTStringLiteral.java \
	  ASTSubAssign.java \
	  ASTSubNode.java \
	  ASTSymbol.java \
	  ASTSynchronizedExpression.java \
	  ASTThis.java \
	  ASTThrow.java \
	  ASTTryExpression.java \
	  ASTUnaryMinusNode.java \
	  ASTUnaryPlusNode.java \
	  ASTVariable.java \
	  ASTVectorExpression.java \
	  ASTWhileExpression.java \
	  ASTXorAssign.java \
	  AbstractFunction.java \
	  AssignmentException.java \
	  Break.java \
	  BuiltInFunction.java \
	  CallInfo.java \
	  Closure.java \
	  Scope.java \
	  Continue.java \
	  Demi.java \
	  DemiException.java \
	  DemiShell.java \
	  EvalFunction.java \
	  ExitFunction.java \
	  Function.java \
	  IllegalTypeException.java \
	  IncludeFunction.java \
	  InspectFunction.java \
	  InternFunction.java \
	  JJTParserState.java \
	  Jump.java \
	  LiteralNode.java \
	  LoadFunction.java \
	  Main.java \
	  Module.java \
	  ModuleFunction.java \
	  NameError.java \
	  Node.java \
	  Numeric.java \
	  PFunction.java \
	  ParseException.java \
	  Parser.java \
	  ParserConstants.java \
	  ParserTokenManager.java \
	  ParserTreeConstants.java \
	  PrintFunction.java \
	  PrintlnFunction.java \
	  Range.java \
	  RequireFunction.java \
	  Return.java \
	  SimpleNode.java \
	  SpecialVariable.java \
	  SpecialVariableGetter.java \
	  SpecialVariableSetter.java \
	  Symbol.java \
	  SymbolTable.java \
	  Token.java \
	  TokenMgrError.java \

OBJS	= ASCII_UCodeESC_CharStream.class \
	  ASTAddAssign.class \
	  ASTAddNode.class \
	  ASTAllocationExpression.class \
	  ASTAndAssign.class \
	  ASTAndNode.class \
	  ASTArgumentList.class \
	  ASTArrayAccess.class \
	  ASTArrayExpression.class \
	  ASTAssignment.class \
	  ASTBitwiseAndNode.class \
	  ASTBitwiseComplNode.class \
	  ASTBitwiseOrNode.class \
	  ASTBitwiseXorNode.class \
	  ASTBooleanLiteral.class \
	  ASTBreak.class \
	  ASTCharacterLiteral.class \
	  ASTClassExpression.class \
	  ASTClosure.class \
	  ASTContinue.class \
	  ASTDivAssign.class \
	  ASTDivNode.class \
	  ASTEqualNode.class \
	  ASTException.class \
	  ASTExpressionList.class \
	  ASTFloatLiteral.class \
	  ASTForExpression.class \
	  ASTFunctionCall.class \
	  ASTFunctionDefinition.class \
	  ASTGlobalVariable.class \
	  ASTGreaterEqualNode.class \
	  ASTGreaterThanNode.class \
	  ASTHashExpression.class \
	  ASTIdentityEqualNode.class \
	  ASTIfExpression.class \
	  ASTImport.class \
	  ASTInNode.class \
	  ASTInstanceOfNode.class \
	  ASTIntegerLiteral.class \
	  ASTLShiftAssign.class \
	  ASTLShiftNode.class \
	  ASTLessEqualNode.class \
	  ASTLessThanNode.class \
	  ASTMemberAccess.class \
	  ASTModAssign.class \
	  ASTModNode.class \
	  ASTModuleDefinition.class \
	  ASTMulAssign.class \
	  ASTMulNode.class \
	  ASTNotEqualNode.class \
	  ASTNotNode.class \
	  ASTNullLiteral.class \
	  ASTOrAssign.class \
	  ASTOrNode.class \
	  ASTPostDecNode.class \
	  ASTPostIncNode.class \
	  ASTPrimarySuffix.class \
	  ASTPreDecNode.class \
	  ASTPreIncNode.class \
	  ASTRShiftAssign.class \
	  ASTRSignedShiftNode.class \
	  ASTRUShiftAssign.class \
	  ASTRUnsignedShiftNode.class \
	  ASTRangeNode.class \
	  ASTReturn.class \
	  ASTStaticMemberAccess.class \
	  ASTStringLiteral.class \
	  ASTSubAssign.class \
	  ASTSubNode.class \
	  ASTSymbol.class \
	  ASTSynchronizedExpression.class \
	  ASTThis.class \
	  ASTThrow.class \
	  ASTTryExpression.class \
	  ASTUnaryMinusNode.class \
	  ASTUnaryPlusNode.class \
	  ASTVariable.class \
	  ASTVectorExpression.class \
	  ASTWhileExpression.class \
	  ASTXorAssign.class \
	  AbstractFunction.class \
	  AssignmentException.class \
	  Break.class \
	  BuiltInFunction.class \
	  CallInfo.class \
	  Closure.class \
	  Scope.class \
	  Continue.class \
	  Demi.class \
	  DemiException.class \
	  DemiShell.class \
	  EvalFunction.class \
	  ExitFunction.class \
	  Function.class \
	  IllegalTypeException.class \
	  IncludeFunction.class \
	  InspectFunction.class \
	  InternFunction.class \
	  JJTParserState.class \
	  Jump.class \
	  LiteralNode.class \
	  LoadFunction.class \
	  Main.class \
	  Module.class \
	  ModuleFunction.class \
	  NameError.class \
	  Node.class \
	  Numeric.class \
	  PFunction.class \
	  ParseException.class \
	  Parser.class \
	  ParserConstants.class \
	  ParserTokenManager.class \
	  ParserTreeConstants.class \
	  PrintFunction.class \
	  PrintlnFunction.class \
	  Range.class \
	  RequireFunction.class \
	  Return.class \
	  SimpleNode.class \
	  SpecialVariable.class \
	  SpecialVariableGetter.class \
	  SpecialVariableSetter.class \
	  Symbol.class \
	  SymbolTable.class \
	  Token.class \
	  TokenMgrError.class \

.SUFFIXES: .class .java .jj .jjt

.java.class:
	$(JAVAC) $<

.jj.java:
	$(JAVACC) $<

.jjt.jj:
	$(JJTREE) $<

all: $(OBJS)

clean:
	rm -f *.class *~

tar: $(OBJS) $(PKGDIR)/demi/elisp/demi-mode.el
	rm -f *~
	rm -f ../util/*~
	rm -f $(PKGDIR)/demi/bin/*~
	rm -f $(PKGDIR)/demi/lib/*~
	rm -f $(PKGDIR)/demi/doc/*~
	rm -f $(PKGDIR)/demi/example/*~
	cd $(PKGDIR); \
	rm -f demi-$(VERSION).tar.gz; \
	tar zcvf demi-$(VERSION).tar.gz demi

zip: $(OBJS) $(PKGDIR)/demi/elisp/demi-mode.el
	rm -f *~
	rm -f ../util/*~
	rm -f $(PKGDIR)/demi/bin/*~
	rm -f $(PKGDIR)/demi/lib/*~
	rm -f $(PKGDIR)/demi/doc/*~
	rm -f $(PKGDIR)/demi/example/*~
	cd $(PKGDIR); \
	rm -f demi-$(VERSION).zip; \
	zip -r demi-$(VERSION).zip demi

Parser.class: Parser.java
Parser.java: Parser.jj
Parser.jj: Parser.jjt

$(PKGDIR)/demi/elisp/demi-mode.el: /home/shugo/emacs/lisp/demi-mode.el
	cp /home/shugo/emacs/lisp/demi-mode.el $(PKGDIR)/demi/elisp
