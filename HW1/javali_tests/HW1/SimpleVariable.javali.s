  # Emitting class Main {...}
    # Emitting void main(...) {...}
    .text
    .globl _main
_main:
      # Emitting int i0
      # Emitting i0 = 5
        # Emitting 5
        movl $5, %edi
      # Emitting write(i0)
      call label0
label0:
      popl %esi
      leal int_format.str-label0(%esi), %esi
      # Stack pad for 16 byte alignment requirement on OSX
      addl $4, %esp
      movl %esi, 0(%esp)
      movl $i0, %esi
      movl 0(%esi), %esi
      movl %esi, 4(%esp)
      call _printf
      # Realign Stack
      subl $4, %esp
      # Emitting writeln()
      call label1
label1:
      popl %esi
      leal newline.str-label1(%esi), %esi
      # Stack pad for 16 byte alignment requirement on OSX
      addl $4, %esp
      movl %esi, 0(%esp)
      call _printf
      # Realign Stack
      subl $4, %esp
    # Stack pad for _exit
    addl $4, %esp
    movl $0, 0(%esp)
    call _exit
    .data
i0:
    .long 0
    .cstring
int_format.str:
    .asciz "%d"
newline.str:
    .asciz "\n"
