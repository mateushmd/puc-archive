module p01a;
	reg a, b, c, d;
	wire s;
	
	fabcd f(s, a, b, c, d);
	
	initial
	begin: main
		integer i;
					
		$monitor("%b %b %b %b | %b", a, b, c, d, s);
		
		a = 0;
		b = 0;
		c = 0;
		d = 0;
 		
		for(i = 0; i < 15; i++) begin
			#1;
	
			d = ~d;
			
			if((i + 1) % 2 == 0) begin
				c = ~c;
			end
			
			if((i + 1) % 4 == 0) begin
				b = ~b;
			end
			
			if((i + 1) % 8 == 0) begin
				a = ~a;
			end
			
		end
	end
endmodule

module fabcd (output s, input a, b, c, d);
	assign s = (~a & ~b & c & ~d) | (~a & b & ~c & ~d) | (~a & b & c & d) | 
		(a & ~b & c & d) | (a & b & ~c & ~d);
endmodule