/*
	Guia_0301.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0301;
	reg [7 : 0] binaries [0 : 5];
	reg [7 : 0] ans [0 : 5];
	
	initial
	begin: main
		integer i;
		
		binaries[0] = 'b1010;
		ans[0] = ~binaries[0];
		
		binaries[1] = 'b1101;
		ans[1] = ~binaries[1];
		
		binaries[2] = 'b101001;
		binaries[3] = 'b101111;
		binaries[4] = 'b110100;
		
		for(i = 2; i < 5; i++) begin
			ans[i] = ~binaries[i] + 1;	
		end
		
		$display("a) C(1,6) %b = %b", binaries[0][3 : 0], ans[0][5 : 0]);
		$display("b) C(1,8) %b = %b", binaries[1][3 : 0], ans[1][7 : 0]);
		$display("c) C(2,6) %b = %b", binaries[2][5 : 0], ans[2][5 : 0]);
		$display("d) C(2,7) %b = %b", binaries[3][5 : 0], ans[3][6 : 0]);
		$display("e) C(2,8) %b = %b", binaries[4][5 : 0], ans[4][7 : 0]);
	end
endmodule

/*
	Saída:
	
	a) C(1,6) 1010 = 110101
	b) C(1,8) 1101 = 11110010
	c) C(2,6) 101001 = 010111
	d) C(2,7) 101111 = 1010001
	e) C(2,8) 110100 = 11001100
*/