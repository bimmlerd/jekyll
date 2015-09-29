.text
	.globl	_main
_main:
# RELEVANT SECTION
	call	L0$pb
L0$pb:
	popl	%eax
	leal	int_format.str-L0$pb(%eax), %eax
	addl	$4, %esp	# stack pad for 16-byte align
	movl	%eax, (%esp)
	movl	$30, %eax
	movl	%eax, 4(%esp)
	call	_printf
# END RELEVANT SECTION
	movl	$0, (%esp)
	call	_exit

# RELEVANT SECTION
.cstring
int_format.str:
	.asciz	"%d"
# END RELEVANT SECTION