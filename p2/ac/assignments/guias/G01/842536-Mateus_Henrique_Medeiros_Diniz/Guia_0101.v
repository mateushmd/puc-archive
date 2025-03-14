/*
	Guia_0101.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0101;
	reg [9 : 0] nums [0 : 4]; //Array de 5 posições de 10 bits
	
	initial
	begin: main
		integer i;
	
		nums[0] = 10'd26; //10'd => Inteiro de 10 bits
		nums[1] = 10'd53;
		nums[2] = 10'd713;
		nums[3] = 10'd213;
		nums[4] = 10'd365;
		
		for(i = 0; i < 5; i++) begin 
			$display("%c) %10b (2)", "a" + i, nums[i]);
		end
	end	
endmodule

/*
	Saída:
	
	a) 0000011010 (2)
	b) 0000110101 (2)
	c) 1011001001 (2)
	d) 0011010101 (2)
	e) 0101101101 (2)
*/