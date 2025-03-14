/*
	Guia_0402c.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0402c;
	reg x, y;
	wire s1;
	wire s2;
	
	fxy f ( s1, s2, x, y);
	
	initial 
	begin: main
		integer i;

		$display("s1 = (x' . y')' . (x + y)");
		$display("s2 = x + y");
		$display("x y | s1 | s2");
		$monitor("%b %b | %b  | %b", x, y, s1, s2);
		
		x = 0;
		y = 0;
		
		for(i = 0; i < 3; i++) begin
			#1;
			y = ~y;
			if((i + 1) % 2 == 0) begin
				x = ~x;
			end
		end
	end
endmodule

module fxy (output s1, s2, input x, y);
	assign s1 = ~(~x & ~y) & (x | y);
	assign s2 = x | y;
endmodule