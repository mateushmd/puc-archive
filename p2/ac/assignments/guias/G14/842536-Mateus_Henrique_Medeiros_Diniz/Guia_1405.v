/*
    Guia_1405.v
    842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1405;
    reg clk, clear, preset;
	reg [5:0] ld;
    wire q; 
    
    parallel_to_serial pts (q, clk, preset, clear, ld);
    
    initial 
    begin: main
        integer i;
        clk = 0;
        clear = 1;
		#1 clear = 0;
        preset = 1;
		ld = 6'b101010;
		
		#1 preset = 0;
        
        $display("%d | %b", i, q);
        
        for(i = 0; i < 6; i++) begin
            #1 clk = ~clk;
            #1 clk = ~clk;
            $display("%d | %b", i, q);
        end
    end
endmodule

module parallel_to_serial (output q_out,
                           input clk, preset, clear,
						   input [5:0] ld);
	wire [5:0] q;
	wire [5:0] d;
    wire unused_qnot;
    
	assign d[0] = preset & ld[0];
	assign d[1] = preset & ld[1];
	assign d[2] = preset & ld[2];
	assign d[3] = preset & ld[3];
	assign d[4] = preset & ld[4];
	assign d[5] = preset & ld[5];
	
    dff d0 (q[0], unused_qnot, q[1], clk, d[0], clear);
    dff d1 (q[1], unused_qnot, q[2], clk, d[1], clear);
    dff d2 (q[2], unused_qnot, q[3], clk, d[2], clear);
    dff d3 (q[3], unused_qnot, q[4], clk, d[3], clear);
    dff d4 (q[4], unused_qnot, q[5], clk, d[4], clear);
    dff d5 (q[5], unused_qnot, 1'b0, clk, d[5], clear);
    
    assign q_out = q[0];
endmodule

module dff ( output reg q, output qnot,
             input d, input clk,
             input preset, input clear );
    
    assign qnot = ~q;
    
    always @( posedge clk or posedge preset or posedge clear )
    begin
        if (clear) begin
            q <= 0;
        end
        else if (preset) begin
            q <= 1;
        end
        else begin
            q <= d;
        end
    end
endmodule
