/*
	Guia_0305.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0305;
	reg [9 : 0] n1 [0 : 4];
	reg [9 : 0] n2 [0 : 4];
	reg [9 : 0] c2n2 [0 : 4];
	reg [9 : 0] ans [0 : 4];
	
	initial
	begin: main
		integer i;
		
		n1[0] = 'b0110101;
		n1[1] = 'b0101_1001;
		n1[2] = 'b0100111;
		n1[3] = 9'hc5;
		n1[4] = 8'h7e;
		
		n2[0] = 'b0001011;
		n2[1] = 'b0011_0010;
		n2[2] = 7'h3d;
		n2[3] = 'b001011001;
		n2[4] = 8'h2d;
		
		for(i = 0; i < 5; i++) begin
			c2n2[i] = ~ n2[i];
			c2n2[i] = c2n2[i] + 1;
			
			ans[i] = n1[i] + c2n2[i];
		end
		
		$display("a) %b - %b = %b", n1[0], n2[0], ans[0]);
		$display("b) %b,%b - %o,%o = %b,%b", n1[1][7 : 5], n1[1][4 : 0], n2[1][5 : 4], n2[1][3 : 1], ans[1][6 : 4], ans[1][3 : 0]);
		$display("c) %d%d%d - %x = %b", n1[2][5 : 4], n1[2][3 : 2], n1[2][1 : 0], n2[2], ans[2]);
		$display("d) %x - %b = %b", n1[3], n2[3], ans[3]);
		$display("e) %x - %x = %b", n1[4], n2[4], ans[4]);
	end
endmodule

/*
	Saída:
	
	a) 0000110101 - 0000001011 = 0000101010
	b) 010,11001 - 3,1 = 010,0111
	c) 213 - 03d = 1111101010
	d) 0c5 - 0001011001 = 0001101100
	e) 07e - 02d = 0001010001
*/