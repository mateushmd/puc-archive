/*
	Guia_0602c.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0602c;
	reg x, y, z;
	wire s;
	reg[2 : 0] m;
	
	fxyz f (s, x, y, z);
	
	initial 
	begin: main
		integer i;
		
		$display("s = (y | ~z) & (x | ~y)");
		
		$display("m x y z | s");
		$monitor("%d %b %b %b | %b", m, x, y, z, s);
		
		m = 0;
		x = 0;
		y = 0;
		z = 0;
		
		for(i = 0; i < 7; i++) begin
			#1
			z = ~z;
			
			if((i + 1) % 2 == 0) begin
				y = ~y;
			end
			
			if((i + 1) % 4 == 0) begin
				x = ~x;
			end
			
			m++;
		end
	end
endmodule

module fxyz (output s, input x, y, z);
	assign s = (y | ~z) & (x | ~y);
endmodule