/*
	Guia_1407.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1407;
	reg clk, clear, preset;
	reg [4:0] ld;
	wire [4:0] q; 
	
	rtring rtring(q, clk, preset, clear, ld);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		preset = 0;
		clear = 1;
		
		#1 clear = 0;
		
		preset = 1;
		ld = 5'b11011;
		
		#1 preset = 0;
		
		$display("%d | %b", i, q);
		
		for(i = 0; i < 12; i++) begin
			#1
			clk = ~clk;
			#1
			clk = ~clk;
			$display("%d | %b", i, q);
		end
	end
endmodule

module rtring (output [4:0] q_out,
			input clk, preset, clear,
			input [4:0] ld);
			
	wire [4:0] q, d;
	wire qnot, unused_qnot;
	
	assign d[0] = preset & ld[0];
	assign d[1] = preset & ld[1];
	assign d[2] = preset & ld[2];
	assign d[3] = preset & ld[3];
	assign d[4] = preset & ld[4];
	
	dff d4 (q[4], unused_qnot, qnot, clk, d[0], clear);
	dff d3 (q[3], unused_qnot, q[4], clk, d[1], clear);
	dff d2 (q[2], unused_qnot, q[3], clk, d[2], clear);
	dff d1 (q[1], unused_qnot, q[2], clk, d[3], clear);
	dff d0 (q[0], qnot, q[1], clk, d[4], clear);
	
	assign q_out = q;
endmodule

module dff ( output q, output qnot,
			input d, input clk,
			input preset, input clear );
	reg q, qnot;
	always @( posedge clk or posedge preset or posedge clear )
	begin
		if ( clear ) begin q <= 0; qnot <= 1; end
		else
		if ( preset ) begin q <= 1; qnot <= 0; end
		else
		begin q <= d; qnot <= ~d; end
	end
endmodule