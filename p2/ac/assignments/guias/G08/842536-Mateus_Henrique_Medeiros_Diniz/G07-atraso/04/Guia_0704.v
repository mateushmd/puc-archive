/*
	Guia_0704.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0704;
	reg x, y, select1, select2;
	wire r, s, t, u, v;
	
	f0703 f (r, s, t, u, x, y);
	
	mux m (v, r, s, t, u, select1, select2);
	
	initial
	begin: main
		integer i;
			
		x = 0;
		y = 0;
		select1 = 0;
		select2 = 0;

		$display("select1 select2 x y | v");
		
		for(i = 0; i < 16; i++) begin
			#1;
			
			$display("   %b       %b    %b %b | %b", select1, select2, x, y, v);
			
			y = ~y;
			
			if((i + 1) % 2 == 0)
				x = ~x;
				
			if((i + 1) % 8 == 0)
				select1 = ~select1;
				
			if((i + 1) % 4 == 0)
				select2 = ~select2;
		end
	end
endmodule

module f0703 (output r, s, t, u, input a, b);
	nor NOR1 (r, a, b);
	or OR1 (s, a, b);
	
	xor XOR1(t, a, b);
	xnor XNOR1(u, a, b);
endmodule

module mux (output s, input a, b, c, d, select1, select2);
	wire not_select1, not_select2;
	wire sa, sb, sc, sd;
	
	not NOT1 (not_select1, select1);
	not NOT2 (not_select2, select2);
	
	and AND1 (sa, a, not_select1, not_select2);
	and AND2 (sb, b, not_select1, select2);
	and AND3 (sc, c, select1, not_select2);
	and AND4 (sd, d, select1, select2);
	
	or OR1 (s, sa, sb, sc, sd);
endmodule