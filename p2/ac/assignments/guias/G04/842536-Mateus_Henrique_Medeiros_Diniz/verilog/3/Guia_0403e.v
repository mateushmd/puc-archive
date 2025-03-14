/*
	Guia_0403e.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0403e;
	reg x, y, w, z;
	wire s;
	
	fxywz f ( s, x, y, w, z);
	
	initial 
	begin: main
		integer i;

		$display("x y z w | SoP (0, 2, 5, 7, 8, 11)");
		$monitor("%b %b %b %b | %b", x, y, w, z, s);
		
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

module fxywz (output s, input x, y, w, z);
	assign s = (~x & ~y & ~w & ~z) | (~x & ~y & w & ~z) | (~x & y & ~w & z) | (~x & y & w & z) | (x & ~y & ~w & ~z) | (x & ~y & w & z);
endmodule