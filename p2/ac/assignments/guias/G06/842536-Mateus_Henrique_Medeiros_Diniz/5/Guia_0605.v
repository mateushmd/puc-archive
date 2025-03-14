/*
	Guia_0605.v
	842536 - Mateus Henrique Medeiros Diniz
*/
module Guia_0605;
	reg x, y, z;
	wire s, t;
	reg[2 : 0] m;
	
	fxyz f (s, t, x, y, z);
	
	initial 
	begin: main
		integer i;
		
		$display("s = (~(~x | ~y) & ~(x & y)) | ~((y & z) | ~x)");
		$display("t = (x & ~y) | (x & ~z)");
		
		$display("m x y z | s t");
		$monitor("%d %b %b %b | %b %b", m, x, y, z, s, t);
		
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

module fxyz (output s, t, input x, y, z);
	assign s = (~(~x | ~y) & ~(x & y)) | ~((y & z) | ~x);
	assign t = (x & ~y) | (x & ~z);
endmodule