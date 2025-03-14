/*
	R_0101c.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module R0101c;
	wire s;
	reg a, b, c, d;
	
	PoS pos (s, a, b, c, d);
	
	initial
	begin: main
		integer i;
		
		$display("a b c d | s");
		
		$monitor("%b %b %b %b | %b", a, b, c, d, s);
		
		a = 0;
		b = 0;
		c = 0;
		d = 0;
		
		for(i = 1; i <= 15; i++) begin	
			#1
			
			d = ~d;
		
			if(i % 2 == 0) begin
				c = ~c;
			end
			
			if(i % 4 == 0) begin
				b = ~b;
			end
			
			if(i % 8 == 0) begin
				a = ~a;
			end
		end
	end
endmodule

module PoS (output s, input a, b, c, d);
    assign s = (a & ~c) | (b & ~c & ~d) | (a & b & ~d);
endmodule
