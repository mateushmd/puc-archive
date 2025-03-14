/*
	Guia_1103.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`define found 1
`define notfound 0

module Guia_1103;
    reg clk, reset, x;
    wire m;

    moore m1 (m, x, clk, reset);

    initial begin
        $display("  t | x | m");
        
        clk = 1;
        reset = 0;
        x = 1;

        #5 reset = 1;
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #10 x = 0;
        #10 x = 1;
        #30 $finish;
    end

    always #5 clk = ~clk;

    always @(posedge clk) begin
        $display("%3d | %b | %b", $time, x, m);
    end
endmodule


module moore (
    output reg y,
    input x,
    input clk,
    input reset
);
    parameter start  = 3'b000,
              id1    = 3'b001,
              id10   = 3'b011,
              id101  = 3'b010,
              id1010 = 3'b110;

    reg [2:0] e1;
    reg [2:0] e2;

    always @(*) begin
        case (e1)
            start: begin
				if(x) e2 = id1;
				else e2 = start;
			end
            id1: begin
				if(x) e2 = id1;
				else e2 = id10;
			end
            id10: begin
				if(x) e2 = id101;
				else e2 = start;
			end
            id101: begin
				if(x) e2 = id1;
				else e2 = id1010;
			end
            id1010: begin
				if(x) e2 = id1;
				else e2 = start;
			end

            default:
                e2 = 3'bxxx;
        endcase
    end

    always @(posedge clk or negedge reset) begin
        if (!reset)
            e1 <= start;
        else
            e1 <= e2;
    end

    always @(*) begin
        y = e1[2];
    end
endmodule
