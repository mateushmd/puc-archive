/*
	R_0102a.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module R0102a;
	wire s;
	reg a, c;
	
	MUX mux (s, a, ~a, c);
	
	initial
	begin: main
		integer i;
		
		$display("a c | s");	
		
		a = 0;
		c = 0;
		
		$monitor("%b %b | %b", a, c, s);
		
		for(i = 1; i <= 3; i++) begin
			#1
			
			c = ~c;
			
			if(i % 2 == 0)
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
