/*
	Guia_0405c.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0405c;
	reg x, y, z;
	wire s1, s2;
	
	fxy f ( s1, s2, x, y, z);
	
	initial 
	begin: main
		integer i;

		$display("x y z | SoP | PoS");
		$monitor("%b %b %b |  %b  |  %b", x, y, z, s1, s2);
		
		x = 0;
		y = 0;
		z = 0;
		
		for(i = 0; i < 7; i++) begin
			#1;
			z = ~z;
			if((i + 1) % 2 == 0) begin
				y = ~y;
			end
			
			if((i + 1) % 4 == 0) begin
				x = ~x;
			end
		end
	end
endmodule

module fxy (output s1, s2, input x, y, z);
	assign s1 = (~x & ~y & ~z) | (~x & y & z) | (x & ~y & ~z) | (x & ~y & z) | (x & y & z);
	assign s2 = (x | y | ~z) & (x | ~y | z) & (~x | ~y | z);
endmodule