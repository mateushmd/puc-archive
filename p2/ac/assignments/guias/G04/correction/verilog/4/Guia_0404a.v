/*
	Guia_0404a.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0404a;
	reg x, y, z;
	wire s;
	
	fxyz f ( s, x, y, z);
	
	initial 
	begin: main
		integer i;

		$display("x y z | PoS (1, 2, 6, 7)");
		$monitor("%b %b %b | %b", x, y, z, s);
		
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

module fxyz (output s, input x, y, z);
	assign s = (x | y | ~z) & (x | ~y | z) & (~x | ~y | z) & (~x | ~y | ~z);
endmodule