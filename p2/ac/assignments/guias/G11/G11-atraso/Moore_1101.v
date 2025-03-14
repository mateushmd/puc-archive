/*
	Mealy_1101.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`define found 1
`define notfound 0

module mealy_1101 ( output reg y, input x, clk, reset );
    parameter start = 2'b00,
              id1   = 2'b01,
              id11  = 2'b11,
              id110 = 2'b10;

    reg [1:0] e1;
    reg [1:0] e2;

    always @(x or e1) begin
        
        case (e1)
            start: begin
                if (x) e2 = id1;
                else e2 = start;
            end

            id1: begin
                if (x) e2 = id11;
                else e2 = start;
            end

            id11: begin
                if (x) e2 = id11;
                else e2 = id110;
            end

            id110: begin
                if (x) begin
                    e2 = id1;
                    y = `found;
                end else e2 = start;
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
