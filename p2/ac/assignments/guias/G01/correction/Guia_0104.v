/*
	Guia_0104.v
	842536 - Mateus Henrique Medeiros Diniz
*/
module Guia_0104;
	reg [5 : 0] b; //Dígito binário de 6 bits
	
	initial
	begin: main
		b = 6'b10100;
		$display("a) [%d][%d][%d] (4) = %d%d%d (4)", b[5:4], b[3:2], b[1:0], b[5:4], b[3:2], b[1:0]);
		b = 6'b11010;
		$display("b) [%d][%d] (8) = %d%d (8)", b[5:3], b[2:0], b[5:3], b[2:0]);
		b = 6'b100111;
		$display("c) [%x][%x] (16) = %x%x (16)", b[5:4], b[3:0], b[5:4], b[3:0]);
		b = 6'b100101;
		$display("d) [%d][%d] (8) = %d%d (8)", b[5:3], b[2:0], b[5:3], b[2:0]);
		b = 6'b101101;
		$display("a) [%d][%d][%d] (4) = %d%d%d (4)", b[5:4], b[3:2], b[1:0], b[5:4], b[3:2], b[1:0]);	
	end
endmodule

/*
	Saída:
	
	a) [1][1][0] (4) = 110 (4)
	b) [3][2] (8) = 32 (8)
	c) [2][7] (16) = 27 (16)
	d) [4][5] (8) = 45 (8)
	a) [2][3][1] (4) = 231 (4)
*/