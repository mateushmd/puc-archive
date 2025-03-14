/*
	Guia_1302.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1302;
	reg clk, enable, preset;
	wire [4:0] q;
	
	counter c (q, clk, enable, preset);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		preset = 1;
		#1 preset = 0;
		enable = 1;
		
		$display("%d | %b", i, q);
		
		for(i = 0; i < 32; i++) begin
			#1
			clk = ~clk;
			#1
			clk = ~clk;
			
			$display("%d | %b", i, q);
		end
	end
endmodule

module counter (output [4:0] q_out,
			input clk, enable, preset);
			
	wire [4:0] q;
	wire [4:0] qnot;
	
	jkff jk0 (q[0], qnot[0], enable, enable, clk, preset, 1'b0);
	jkff jk1 (q[1], qnot[1], enable, enable, q[0], preset, 1'b0);
	jkff jk2 (q[2], qnot[2], enable, enable, q[1], preset, 1'b0);
	jkff jk3 (q[3], qnot[3], enable, enable, q[2], preset, 1'b0);
	jkff jk4 (q[4], qnot[4], enable, enable, q[3], preset, 1'b0);
	
	assign q_out = qnot;
endmodule

module jkff ( output q, output qnot,
			input j, input k, input clk,
			input preset, input clear );
	reg q, qnot;
	always @( posedge clk or posedge preset or posedge clear )
	begin
		if ( clear ) begin q <= 0; qnot <= 1; end
		else
		if ( preset ) begin q <= 1; qnot <= 0; end
		else
		if (j & ~k) begin q <= 1; qnot <= 0; end
		else
		if (~j & k) begin q <= 0; qnot <= 1; end
		else 
		if(j & k) begin q <= ~q; qnot <= ~qnot; end
	end
endmodule