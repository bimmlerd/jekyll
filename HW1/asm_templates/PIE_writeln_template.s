.text
	.globl	_main
_main:
# RELEVANT SECTION
	call	L0$pb
L0$pb:
	popl	%eax
	leal	newline.str-L0$pb(%eax), %eax
	addl	$4, %esp	# stack pad for 16-byte align
	movl	%eax, (%esp)
	call	_printf
	subl	$4, %esp	# realign stack
# END RELEVANT SECTION
	addl	$4, %esp	# stack pad for _exit
	movl	$0, (%esp)
	call	_exit

# RELEVANT SECTION
	.cstring
newline.str:
	.asciz	"\n"
# END RELEVANT SECTION