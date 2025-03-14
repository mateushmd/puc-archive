/*
	Guia_0103.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0103;
	reg [9 : 0] nums [0 : 3]; //Array de 5 posições de 10 bits
	reg [1 : 0] base4 [0 : 2]; //Array de 3 posições para dígitos de base 4
	
	initial
	begin: main
		integer i, num;
	
		i = 2;
		num = 61;
		
		$write("a) ");
		
		while(num > 3) begin
			base4[i--] = num % 4; 
			num /= 4;
		end
		
		base4[i] = num;
		
		for(i = 0; i < 3; i++) begin
			$write("%d", base4[i]);
		end
			
		$write(" (4)\n");
			
		nums[0] = 10'd53; //10'd => inteiro de 10 bits
		nums[1] = 10'd77;
		nums[2] = 10'd153;
		nums[3] = 10'd753;
		
		$display("b) %o	(8)", nums[0]);
		$display("c) %x (16)", nums[1]);
		$display("d) %x (16)", nums[2]);
		$display("e) %x (16)", nums[3]);
	end

endmodule

/*
	Saída:
	
	a) 331 (4)
	b) 0065 (8)
	c) 04d (16)
	d) 099 (16)
	e) 2f1 (16)
*/