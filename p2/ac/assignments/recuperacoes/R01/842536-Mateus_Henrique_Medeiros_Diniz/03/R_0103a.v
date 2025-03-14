/*
	R_0103a.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module R0103a;
	wire s;
	reg a, b, c;
	
	F f (s, a, b, c);
	
	initial
	begin: main
		integer i;
		
		$display("a b c | s");	
		
		a = 0;
		b = 0;
		c = 0;
		
		$monitor("%b %b %b | %b", a, b, c, s);
		
		for(i = 1; i <= 7; i++) begin
			#1
			
			c = ~c;
			
			if(i % 2 == 0)
				b = ~b;
				
			if(i % 4 == 0)
				a = ~a;
		end
	end
endmodule

module F (output s, input a, b, c);
	assign s = (~a | b) & (b | ~c);
endmodule
