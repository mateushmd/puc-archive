/*
	Guia_0604c.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0604c;
	reg x, y, w, z;
	wire s;
	reg[3 : 0] m;
	
	fxywz f (s, x, y, w, z);
	
	initial 
	begin: main
		integer i;
		
		$display("s = (~x | ~y | ~z) & (~y | ~w | ~z) & (x | ~y | w | z) & (~x | y | w | z)");
		
		$display("m  x y w z | s");
		$monitor("%d %b %b %b %b | %b", m, x, y, w, z, s);
		
		m = 0;
		x = 0;
		y = 0;
		w = 0;
		z = 0;
		
		for(i = 0; i < 15; i++) begin
			#1
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
			
			m++;
		end
	end
endmodule

module fxywz (output s, input x, y, w, z);
	assign s = (~x | ~y | ~z) & (~y | ~w | ~z) & (x | ~y | w | z) & (~x | y | w | z);
endmodule