/*
	Guia_0401e.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0401e;
	reg x, y, z;
	wire s;
	
	fxyz f ( s, x, y, z);
	
	initial 
	begin: main
		integer i;
		
		$display("x y z | (x'+y).(y'+z)'");
		$monitor("%b %b %b |    %b", x, y, z, s);
		
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
	assign s = (~x | y) & ~(~y | z);
endmodule