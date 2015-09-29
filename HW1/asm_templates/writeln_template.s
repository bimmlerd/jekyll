.text
	.globl	_main
_main:
	addl	$4, %esp	# stack pad for 16-byte align
# RELEVANT SECTION
	movl	$newline.str, (%esp)
	call	_printf
# END RELEVANT SECTION
# CHAINABLE
	movl	$newline.str, (%esp)
	call	_printf
# CHAINABLE
	movl	$0, (%esp)
	call	_exit

# RELEVANT SECTION
	.cstring
newline.str:
	.asciz	"\n"
# END RELEVANT SECTION