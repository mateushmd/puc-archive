/*
	Guia_1101.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`define found 1
`define notfound 0

module Guia_1101;
    reg clk, reset, x;
    wire m1;

    FSM fsm1 (m1, x, clk, reset);

    initial begin
        $display("  t | x | fsm");
        
        clk = 1;
        reset = 0;
        x = 0;

        #5 reset = 1;
        #10 x = 1;
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #10 x = 1;
        #10 x = 0;
        #30 $finish;
    end

    always #5 clk = ~clk;

    always @(posedge clk) begin
        $display("%3d | %b |  %b", $time, x, m1);
    end
endmodule

module FSM ( output reg y, input x, clk, reset );
    parameter start  = 3'b000,
              id1    = 3'b001,
              id10   = 3'b011,
              id101  = 3'b010,
              id1010 = 3'b110;
			  
    reg [2:0] e1;
    reg [2:0] e2;
	
    always @(*) begin
        y = `notfound;
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
				else begin
					e2 = id1010;
					y = `found;
				end
            end
			
            default:
                e2 = 3'bxxx;
        endcase
    end

    always @(posedge clk or negedge reset) begin
        if (!reset)
            e1 = start;
        else
            e1 = e2;
    end
endmodule