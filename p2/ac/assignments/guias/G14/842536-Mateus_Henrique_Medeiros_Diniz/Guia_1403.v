/*
	Guia_1403.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1403;
	reg ld, clk, clear, preset;
	wire [5:0] q; 
	
	rring rring(q, clk, ld, preset, clear);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		preset = 0;
		clear = 1;
		
		#1 clk = ~clk;
		#1 clk = ~clk;
		
		clear = 0;
		ld = 1;
		
		#1 clk = ~clk;
		#1 clk = ~clk;
		
		ld = 0;
		
		$display("%d | %b", i, q);
		
		for(i = 0; i < 6; i++) begin
			#1
			clk = ~clk;
			#1
			clk = ~clk;
			$display("%d | %b", i, q);
		end
	end
endmodule

module rring (output [5:0] q_out,
			input clk, ld, preset, clear);
	wire [5:0] q;
	wire d, qnot;
	
	assign d = ld | q[0];
	
	dff d5 (q[5], qnot, d, clk, preset, clear);
	dff d4 (q[4], qnot, q[5], clk, preset, clear);
	dff d3 (q[3], qnot, q[4], clk, preset, clear);
	dff d2 (q[2], qnot, q[3], clk, preset, clear);
	dff d1 (q[1], qnot, q[2], clk, preset, clear);
	dff d0 (q[0], qnot, q[1], clk, preset, clear);
	
	assign q_out = q;
endmodule

module dff ( output q, output qnot,
			input d, input clk,
			input preset, input clear );
	reg q, qnot;
	always @( posedge clk )
	begin
		if ( clear ) begin q <= 0; qnot <= 1; end
		else
		if ( preset ) begin q <= 1; qnot <= 0; end
		else
		begin q <= d; qnot <= ~d; end
	end
endmodule