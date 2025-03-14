/*
	Guia_1201.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1201;
    reg clk, preset, clear;
    reg [3:0] data_in;
    wire [3:0] data_out;

    memory_1x4 mem (
        data_out,
        data_in,
        clk,
        preset,
        clear
    );

    initial begin
        clk = 0;
        forever #5 clk = ~clk;
    end

    initial begin
        preset = 0;
        clear = 1;
        data_in = 4'b0000;
        
        #10 clear = 0;

        #10 data_in = 4'b1010; preset = 1; 
        #10 preset = 0;

        #10 data_in = 4'b1101; preset = 1;
        #10 preset = 0;

        #20 clear = 1;
        #10 clear = 0;

        #20 $finish;
    end

    initial begin
        $monitor("Time=%0d, Data In=%b, Data Out=%b", $time, data_in, data_out);
    end
endmodule

module jkff (
    output reg q,
    output reg qnot,
    input j, input k,
    input clk, input preset, 
    input clear
);
    always @(posedge clk or posedge preset or posedge clear) begin
        if (clear) begin 
            q <= 0; 
            qnot <= 1; 
        end
        else if (preset) begin 
            q <= 1; 
            qnot <= 0; 
        end
        else if (j & ~k) begin 
            q <= 1; 
            qnot <= 0; 
        end
        else if (~j & k) begin 
            q <= 0; 
            qnot <= 1; 
        end
        else if (j & k) begin
            q <= ~q; 
            qnot <= ~qnot; 
        end
    end
endmodule

module memory_1x4 (
    output [3:0] data_out,
    input [3:0] data_in,
    input clk, preset, clear
);
    jkff ff0 (data_out[0], , data_in[0], ~data_in[0], clk, preset, clear);
    jkff ff1 (data_out[1], , data_in[1], ~data_in[1], clk, preset, clear);
    jkff ff2 (data_out[2], , data_in[2], ~data_in[2], clk, preset, clear);
    jkff ff3 (data_out[3], , data_in[3], ~data_in[3], clk, preset, clear);
endmodule
