.text
	.globl	_main
_main:
	addl $4, %esp
# RELEVANT SECTION
	movl $20, %edi
    movl $40, %esi
    movl %esi, %eax
    cdq
    idiv %edi
    movl %eax, %edi
# END RELEVANT SECTION
# CHAINABLE
# CHAINABLE
	movl	$0, (%esp)
	call	_exit
