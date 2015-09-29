.text
	.globl	_main
_main:
	addl	$4, %esp	# stack pad for 16-byte align
# RELEVANT SECTION
	movl	$int_format.str, (%esp)
	movl	$i0, 4(%esp)
	call	_scanf
# END RELEVANT SECTION
# CHAINABLE
	movl	$int_format.str, (%esp)
	movl	$i0, 4(%esp)
	call	_scanf
# CHAINABLE
# PRINT IT
	movl	$int_format.str, (%esp)
	movl	$i0, %eax
	movl	(%eax), %eax
	movl	%eax, 4(%esp)
	call	_printf
# PRINT IT
	movl	$0, (%esp)
	call	_exit

# RELEVANT SECTION
.data
i0:
    .long 0
.cstring
int_format.str:
	.asciz	"%d"
# END RELEVANT SECTION