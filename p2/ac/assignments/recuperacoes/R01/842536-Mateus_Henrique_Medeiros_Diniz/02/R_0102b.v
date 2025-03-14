/*
	R_0102b.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module R0102a;
	wire s1, s2;
	reg a, b, c;
	
	MUX mux1 (s1, a, ~a, b); 
	MUX mux2 (s2, c, ~c, s1);
	
	initial
	begin: main
		integer i;
		
		$display("a b c | s");	
		
		a = 0;
		b = 0;
		c = 0;
		
		$monitor("%b %b %b | %b", a, b, c, s2);
		
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

module MUX (output s, input a, b, c);
	wire s1, s2;
	
	and (s1, ~a, b);
	and (s2, a, c);
	
	assign s = s1 | s2;
endmodule
