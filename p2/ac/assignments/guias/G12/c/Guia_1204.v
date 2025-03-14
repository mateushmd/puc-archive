/*
	Guia_1204.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1204;
    reg clk, preset, clear;
    reg [1:0] select;
    reg [7:0] data_in;
    wire [7:0] data_out;

    memory_4x8 mem (
        data_out,
        data_in,
        clk,
        preset,
        clear,
        select
    );

    initial begin
        clk = 0;
        forever #5 clk = ~clk;
    end

    initial begin
        preset = 0;
        clear = 1;
        select = 2'b00;
        data_in = 8'b00000000;

        #10 clear = 0;

        #10 data_in = 8'b10101010; preset = 1; select = 2'b00;
        #10 preset = 0;

        #10 data_in = 8'b11001100; select = 2'b01; preset = 1;
        #10 preset = 0;

        #10 data_in = 8'b11110000; select = 2'b10; preset = 1;
        #10 preset = 0;

        #10 data_in = 8'b00001111; select = 2'b11; preset = 1;
        #10 preset = 0;

        #20 clear = 1;
        #10 clear = 0;

        #20 $finish;
    end

    initial begin
        $monitor("Time=%0d, Data In=%b, Data Out=%b, Select=%b", $time, data_in, data_out, select);
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

module memory_1x8 (
    output [7:0] data_out,
    input [7:0] data_in,
    input clk, preset, clear
);
    jkff ff0 (data_out[0], , data_in[0], ~data_in[0], clk, preset, clear);
    jkff ff1 (data_out[1], , data_in[1], ~data_in[1], clk, preset, clear);
    jkff ff2 (data_out[2], , data_in[2], ~data_in[2], clk, preset, clear);
    jkff ff3 (data_out[3], , data_in[3], ~data_in[3], clk, preset, clear);
    jkff ff4 (data_out[4], , data_in[4], ~data_in[4], clk, preset, clear);
    jkff ff5 (data_out[5], , data_in[5], ~data_in[5], clk, preset, clear);
    jkff ff6 (data_out[6], , data_in[6], ~data_in[6], clk, preset, clear);
    jkff ff7 (data_out[7], , data_in[7], ~data_in[7], clk, preset, clear);
endmodule

module memory_2x8 (
    output [7:0] data_out,
    input [7:0] data_in,
    input clk, preset, clear,
    input select
);
    wire [7:0] mem0_out, mem1_out;

    memory_1x8 mem0 (mem0_out, data_in, clk, preset, clear);
    memory_1x8 mem1 (mem1_out, data_in, clk, preset, clear);

    assign data_out = select ? mem1_out : mem0_out;
endmodule

module memory_4x8 (
    output [7:0] data_out,
    input [7:0] data_in,
    input clk, preset, clear,
    input [1:0] select
);
    wire [7:0] mem0_out, mem1_out, mem2_out, mem3_out;

    memory_2x8 mem0 (mem0_out, data_in, clk, preset, clear, select[0]);
    memory_2x8 mem1 (mem1_out, data_in, clk, preset, clear, select[0]);

    assign data_out = (select == 2'b00) ? mem0_out :
                      (select == 2'b01) ? mem1_out :
                      (select == 2'b10) ? mem2_out :
                                          mem3_out;
endmodule
