/*
	Moore_1101.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`define found 1
`define notfound 0

module moore_1101 ( output reg y, input x, clk, reset );
	
	parameter
		start = 3'b000,
		id1 = 3'b001,
		id11 = 3'b011,
		id110 = 3'b010,
		id1101 = 3'b110;
	
	reg [2:0] e1;
	reg [2:0] e2;
	
	always @( x or e1 ) begin
		case( e1 ) 
			start: 
				if ( x ) e2 = id1;
				else e2 = start;
			id1:
				if ( x ) e2 = id11;
				else e2 = start;
			id11:
				if ( x ) e2 = id11;
				else e2 = id110;
			id110:
				if ( x ) e2 = id1101;
				else e2 = start;
			id1101:
				if ( x ) e2 = id11;
				else e2 = start;
			default:
				e2 = 3'bxxx;
		endcase
	end
	
	always @( posedge clk or negedge reset ) begin
		if ( reset ) e1 = e2;
		else e1 = 0;
	end
	
	always @( e1 ) begin
		y = e1[2];
	end
endmodule