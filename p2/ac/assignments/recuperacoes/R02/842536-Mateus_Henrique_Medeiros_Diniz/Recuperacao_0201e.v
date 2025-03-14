/*
	Recuperacao_0201e.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Recuperacao_0201e;
	reg a, b, c, d;
	wire s;
	
	ff ff1 (a, b, c, d, s);
	
	initial begin:
	main
		integer i;
		
		$display("m a b c d | s");
		$monitor("%0h %b %b %b %b | %b", i, a, b, c, d, s);
		
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

module ff (input a, b, c, d, output s);
	assign s = (c|~d)&(~c|d)&(b|c);
endmodule