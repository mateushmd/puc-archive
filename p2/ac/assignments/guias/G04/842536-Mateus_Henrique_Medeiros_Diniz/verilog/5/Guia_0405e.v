/*
	Guia_0405e.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0405e;
	reg x, y, w, z;
	wire s1, s2;
	
	fxy f ( s1, s2, x, y, w, z);
	
	initial 
	begin: main
		integer i;

		$display("x y w z | SoP | PoS");
		$monitor("%b %b %b %b |  %b  |  %b", x, y, w, z, s1, s2);
		
		x = 0;
		y = 0;
		w = 0;
		z = 0;
		
		for(i = 0; i < 15; i++) begin
			#1;
			z = ~z;
			if((i + 1) % 2 == 0) begin
				w = ~w;
			end
			
			if((i + 1) % 4 == 0) begin
				y = ~y;
			end
			
			if((i + 1) % 8 == 0) begin
				x = ~x;
			end
		end
	end
endmodule

module fxy (output s1, s2, input x, y, w, z);
	assign s1 = (~x & ~y & ~w & ~z) | (~x & ~y & ~w & z) | (~x & ~y & w & z) | (~x & y & ~w & z) | (~x & y & w & ~z) | (x & ~y & ~w & ~z) | (x & ~y & ~w & z) | (x & ~y & w & ~z) | (x & y & ~w & ~z) | (x & y & w & ~z) | (x & y & w & z);
	assign s2 = (x | y | ~w | z) & (x | ~y | w | z) & (x | ~y | ~w | ~z) & (~x | y | ~w | ~z) & (~x | ~y | w | ~z);
endmodule