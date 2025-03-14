/*
	Guia_0203.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0203;
	reg [7 : 0] binary;
	
	initial
	begin: main
		binary = 8'b00011110;
		$display("a) 0,%x%x%x (4)", binary[5:4], binary[3:2], binary[1:0]);
		
		binary = 8'b00101001;
		$display("b) 0,%o%o (8)", binary[5:3], binary[2:0]);
		
		binary = 8'b10011000;
		$display("c) 0,%x%x (16)", binary[7:4], binary[3:0]);
		
		binary = 8'b01111011;
		$display("d) %o,%o%o (8)", binary[7:6], binary[5:3], binary[2:0]);
		
		binary = 8'b11011001;
		$display("e) %x,%x (16)", binary[7:4], binary[3:0]);
	end
endmodule

/*
	Saída:
	
	a) 0,132 (4)
	b) 0,51 (8)
	c) 0,98 (16)
	d) 1,73 (8)
	e) d,9 (16)
*/