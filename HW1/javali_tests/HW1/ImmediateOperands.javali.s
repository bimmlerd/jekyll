  # Emitting class Main {...}
    # Emitting void main(...) {...}
    .text
    .globl _main
_main:
      # Emitting int i0
      # Emitting i0 = 0
        # Emitting 0
        movl $0, %edi
      # Emitting i0 = (5 + i0)
        # Emitting (5 + i0)
          # Emitting 5
          movl $5, %esi
          # Emitting i0
          movl $i0, %edx
          movl 0(%edx), %edx
        addl %esi, %edx
      # Emitting write(i0)
      call label0
label0:
      popl %ecx
      leal int_format.str-label0(%ecx), %ecx
      # Stack pad for 16 byte alignment requirement on OSX
      addl $4, %esp
      movl %ecx, 0(%esp)
      movl $i0, %ecx
      movl 0(%ecx), %ecx
      movl %ecx, 4(%esp)
      call _printf
      # Realign Stack
      subl $4, %esp
      # Emitting writeln()
      call label1
label1:
      popl %ecx
      leal newline.str-label1(%ecx), %ecx
      # Stack pad for 16 byte alignment requirement on OSX
      addl $4, %esp
      movl %ecx, 0(%esp)
      call _printf
      # Realign Stack
      subl $4, %esp
      # Emitting i0 = (i0 + 5)
        # Emitting (i0 + 5)
          # Emitting i0
          movl $i0, %ecx
          movl 0(%ecx), %ecx
          # Emitting 5
          movl $5, %ebx
        addl %ecx, %ebx
      # Emitting write(i0)
      call label2
label2:
      popl %eax
      leal int_format.str-label2(%eax), %eax
      # Stack pad for 16 byte alignment requirement on OSX
      addl $4, %esp
      movl %eax, 0(%esp)
      movl $i0, %eax
      movl 0(%eax), %eax
      movl %eax, 4(%esp)
      call _printf
      # Realign Stack
      subl $4, %esp
      # Emitting writeln()
      call label3
label3:
      popl %eax
      leal newline.str-label3(%eax), %eax
      # Stack pad for 16 byte alignment requirement on OSX
      addl $4, %esp
      movl %eax, 0(%esp)
      call _printf
      # Realign Stack
      subl $4, %esp
      # Emitting i0 = ((i0 + 5) + 3)
        # Emitting ((i0 + 5) + 3)
          # Emitting (i0 + 5)
            # Emitting i0
            movl $i0, %eax
            movl 0(%eax), %eax
            # Emitting 5
