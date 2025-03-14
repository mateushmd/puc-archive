/*
	R_0103b.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module R0103b;
	wire s;
	reg a, b;
	
	F f (s, a, b);
	
	initial
	begin: main
		integer i;
		
		$display("a b | s");	
		
		a = 0;
		b = 0;
		
		$monitor("%b %b | %b", a, b, s);
		
		for(i = 1; i <= 3; i++) begin
			#1
			
			b = ~b;
			
			if(i % 2 == 0)
				a = ~a;
		end
	end
endmodule

module F (output s, input a, b);
	assign s = ~(~a & b) & ~(~a & ~b);
endmodule
