/*
	Guia_0704.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0704;
	reg x, y, select1, select2, select3;
	wire n, o, p, q, r, s, t, u, v;
	
	f0705 f (n, o, p, q, r, s, t, u, x, y);
	
	mux m (v, n, o, p, q, r, s, t, u, select1, select2, select3);
	
	initial
	begin: main
		integer i;
			
		x = 0;
		y = 0;
		select1 = 0;
		select2 = 0;
		select3 = 0;
		
		$display("select1 select2 select3 x y | v");
		
		for(i = 0; i < 31; i++) begin
			#1;
			
			$display("   %b       %b       %b    %b %b | %b", select1, select2, select3, x, y, v);
			
			y = ~y;
			
			if((i + 1) % 2 == 0)
				x = ~x;
				
			if((i + 1) % 16 == 0)
				select1 = ~select1;
				
			if((i + 1) % 8 == 0)
				select2 = ~select2;
				
			if((i + 1) % 4 == 0)
				select3 = ~select3;
		end
	end
endmodule

module f0705 (output n, o, p, q, r, s, t, u, input a, b);
	not NOT1 (n, a);
	not NOT2 (o, b);
	
	and AND1 (p, a, b);
	nand NAND1 (q, a, b);
	
	or OR1 (r, a, b);
	nor NOR1 (s, a, b);
	
	xor XOR1(t, a, b);
	xnor XNOR1(u, a, b);
endmodule

module mux (output s, input a, b, c, d, e, f, g, h, select1, select2, select3);
	wire not_select1, not_select2, not_select3;
	wire sa, sb, sc, sd, se, sf, sg, sh;
	
	not NOT1 (not_select1, select1);
	not NOT2 (not_select2, select2);
	not NOT3 (not_select3, select3);
	
	and AND1 (sa, a, not_select1, not_select2, not_select3);
	and AND2 (sb, b, not_select1, not_select2, select3);
	and AND3 (sc, c, not_select1, select2, not_select3);
	and AND4 (sd, d, not_select1, select2, select3);
	and AND5 (se, e, select1, not_select2, not_select3);
	and AND6 (sf, f, select1, not_select2, select3);
	and AND7 (sg, g, select1, select2, not_select3);
	and AND8 (sh, h, select1, select2, select3);
	
	or OR1 (s, sa, sb, sc, sd, se, sf, sg, sh);
endmodule