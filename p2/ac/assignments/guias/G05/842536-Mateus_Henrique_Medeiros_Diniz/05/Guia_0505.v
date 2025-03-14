/*
	Guia_0505.v
	842536 - Mateus Henrique Medeiros Diniz
	
	s = ~(a ^ b)
	m a b s
	0 0 0 1
	1 0 1 0
	2 1 0 0
	3 1 1 1
*/

module Guia_0505;
	reg a, b;
	wire s, t;
	
	fab f (s, t, a, b);
	
	initial 
	begin: main
		integer i;
		
		$display("s = ~(a ^ b)");
		$display("t = ~(~(~(a | a) | b) | ~(a | ~(b | b)))");
		
		$display("a b | s t");
		$monitor("%b %b | %b %b", a, b, s, t);
		
		a = 0;
		b = 0;
		
		for(i = 0; i < 3; i++) begin
			#1;	
			b = ~b;
			if((i + 1) % 2 == 0) begin
				a = ~a;
			end
		end
	end
endmodule

module fab (output s, t, input a, b);
	assign s = ~(a ^ b);
	assign t = ~(~(~(a | a) | b) | ~(a | ~(b | b)));
endmodule