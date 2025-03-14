/*
	Guia_0204.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0204;
	reg [15 : 0] num;
	
	initial
	begin: main
		num[15] = 0;
		num[14:13] = 2'd3;
		num[12:11] = 2'd2;
		num[10:9] = 2'd1;
		$display("a) %1b,%2b%2b%2b (2)", num[15], num[14:13], num[12:11], num[10:9]);
		
		num[15] = 0;
		num[14:3] = 12'h3d2;
		$display("b) %1b,%4b%4b%3b (2)", num[15], num[14:11], num[10:7], num[6:4]);
		
		num[15] = 0;
		num[14:6] = 'o751;
		$display("c) %1b,%3b%3b%3b (2)", num[15], num[14:12], num[11:9], num[8:6]);
		
		num[15:3] = 'o7345;
		num[2:0] = 0;
		$display("d) %d%d,%d%d%d%d%d (4)", num[15:14], num[13:12], num[11:10], num[9:8], num[7:6], num[5:4], num[3:2]);
		
		num = 'hfa5e;
		$display("e) %d%d,%d%d%d%d%d%d (16)", num[15:14], num[13:12], num[11:10], num[9:8], num[7:6], num[5:4], num[3:2], num[1:0],);
	end
endmodule

/*
	Saída:
	
	a) 0,111001 (2)
	b) 0,00111101001 (2)
	c) 0,111101001 (2)
	d) 13,13022 (4)
	e) 33,221132 (16)
*/