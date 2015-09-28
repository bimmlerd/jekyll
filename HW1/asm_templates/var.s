	.section	__TEXT,__text,regular,pure_instructions
	.macosx_version_min 10, 10
	.globl	_main
	.align	4, 0x90
_main:                                  ## @main
## BB#0:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$24, %esp
	calll	L0$pb
L0$pb:
	popl	%eax
	leal	L_.str-L0$pb(%eax), %eax
	leal	-8(%ebp), %ecx
	movl	$0, -4(%ebp)
	movl	%eax, (%esp)
	movl	%ecx, 4(%esp)
	calll	_scanf
	movl	-8(%ebp), %ecx
	addl	$1, %ecx
	movl	%ecx, -8(%ebp)
	movl	-8(%ebp), %ecx
	movl	%eax, -12(%ebp)         ## 4-byte Spill
	movl	%ecx, %eax
	addl	$24, %esp
	popl	%ebp
	retl

	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"%d"


.subsections_via_symbols
