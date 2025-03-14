/*
	Guia_0202.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0202;
	function [7 : 0] realtobin(input real number);
		integer i, x;
		reg [7 : 0] bin;
		
		begin
			for(i = 0; i < 8; i++) begin
				bin[i] = 0;
			end
		
			i = 7;
		
			while(number > 0 && i >= 0) begin
				number *= 2;
				
				if(number >= 1) begin
					bin[i] = 1;
					number -= 1.0;
				end
				else begin
					bin[i] = 0;
				end
				
				i--;
			end
			
			realtobin = bin;
		end
	endfunction

	real nums [0:4];

	initial begin: main
		integer i;
		real intpart;
		reg [2 : 0] intpartbin;
	
		nums[0] = 0.375;
		nums[1] = 2.125;
		nums[2] = 3.625;
		nums[3] = 5.03125;
		nums[4] = 6.75;
		
		for(i = 0; i < 5; i++) begin
			intpart = $floor(nums[i]);
			intpartbin = intpart;
			nums[i] -= intpart;
			
			$display("%c) %b,%b (2)", 97 + i, intpartbin, realtobin(nums[i]));
		end
	end
endmodule

/*
	Saída:
	
	a) 000,01100000 (2)
	b) 010,00100000 (2)
	c) 011,10100000 (2)
	d) 101,00001000 (2)
	e) 110,11000000 (2)
*/