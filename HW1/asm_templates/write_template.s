.text
	.globl	_main
_main:
	addl	$4, %esp	# stack pad for 16-byte align
# RELEVANT SECTION
	movl	$int_format.str, (%esp)
	movl	$30, 4(%esp)
	call	_printf
# END RELEVANT SECTION
# CHAINABLE
	movl	$int_format.str, (%esp)
	movl	$30, 4(%esp)
	call	_printf
# CHAINABLE
	movl	$0, (%esp)
	call	_exit

# RELEVANT SECTION
.cstring
int_format.str:
	.asciz	"%d"
# END RELEVANT SECTION