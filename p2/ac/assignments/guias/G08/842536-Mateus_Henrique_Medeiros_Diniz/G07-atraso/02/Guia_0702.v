/*
	Guia_0702.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0702;
	reg x, y, select;
	wire s, t, u;
	
	f0702 f (s, t, x, y);
	
	mux m (u, s, t, select);
	
	initial
	begin: main
		integer i;
			
		x = 0;
		y = 0;
		select = 0;
		
		$display("select x y | u");
		
		for(i = 0; i < 8; i++) begin
			#1;
			
			$display("  %b    %b %b | %b", select, x, y, u);
			
			y = ~y;
			
			if((i + 1) % 2 == 0)
				x = ~x;
				
			if((i + 1) % 4 == 0)
				select = ~select;
		end
	end
endmodule

module f0702 (output s, t, input a, b);
	or OR1 (s, a, b);
	nor NOR1 (t, a, b);
endmodule

module mux (output s, input a, b, select);
	wire not_select;
	wire sa;
	wire sb;
	
	not NOT1 (not_select, select);
	
	and AND1 (sa, a, not_select);
	and AND2 (sb, b, select);
	
	or OR1 (s, sa, sb);
endmodule