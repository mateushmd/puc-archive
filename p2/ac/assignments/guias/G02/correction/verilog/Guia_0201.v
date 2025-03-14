/*
	Guia_0201.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0201;
	function real printdec(input [7:0] binary, input integer dotpos);
		real power2;
		real x;
		integer i;
		
		begin
			power2 = $pow(2, dotpos);
			x = 0;
			
			for (i = 7; i >= 0; i = i - 1) begin
				power2 = power2 / 2.0;
				
				if (binary[i] == 1) begin
					x = x + power2;
				end
			end
			
			printdec = x;
		end
	endfunction

	
	reg [7:0] nums [0:4];
	integer dotpos [0:4];

	initial begin: main
		integer i;
		
		nums[0] = 8'b00001100;
		dotpos[0] = 1;
		
		nums[1] = 8'b00100100;
		dotpos[1] = 1;
		
		nums[2] = 8'b01010100;
		dotpos[2] = 1;
		
		nums[3] = 8'b11110100;
		dotpos[3] = 1;
		
		nums[4] = 8'b11110010;
		dotpos[4] = 2;
		
		for(i = 0; i < 5; i++) begin
			$display("%c) %f", 97 + i, printdec(nums[i], dotpos[i]));
		end
	end
endmodule

/*
	Saída:
	
	a) 0.093750
	b) 0.281250
	c) 0.656250
	d) 1.906250
	e) 3.781250
*/