/*
	Guia_0303.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0303;
	reg [6 : 0] nums [0 : 5];
	reg [6 : 0] ans;
	integer size [0 : 5];
	
	initial
	begin: main
		integer i;
				
		size[0] = 5;
		size[1] = 6;
		size[2] = 6;
		size[3] = 7;
		size[4] = 7;
		
		nums[0] = 'b1110110;
		nums[1] = 'b1110011;
		nums[2] = 'b1100100;
		nums[3] = 'b1011011;
		nums[4] = 'b1110011;
		
		for(i = 0; i < 5; i++) begin
			ans = nums[i] - 1;
			ans = ~ans;
			$display("%c) C(2,%0d) %b (2) = %0d (10)", 97 + i, size[i], nums[i], ans);
		end
	end
endmodule

/*
	Saída:
	
	a) C(2,5) 1110110 (2) = 10 (10)
	b) C(2,6) 1110011 (2) = 13 (10)
	c) C(2,6) 1100100 (2) = 28 (10)
	d) C(2,7) 1011011 (2) = 37 (10)
	e) C(2,7) 1110011 (2) = 13 (10)
*/