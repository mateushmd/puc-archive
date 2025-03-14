/*
	Guia_0302.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0302;
	reg [9 : 0] nums [0 : 5];
	reg [9 : 0] ans [0 : 5];
	
	initial
	begin: main
		integer i;
		
		nums[0] = 6'b111001;
		ans[0] = ~nums[0];
		
		nums[1] = 8'hb2;
		ans[1] = ~nums[1];
		
		nums[2] = 6'b101101;
		nums[3] = 10'o146;
		nums[4] = 8'h6f;
		
		for(i = 2; i < 5; i++) begin
			ans[i] = ~nums[i] + 1;	
		end
		
		$display("a) C(1,6) %d%d%d (4) = %b", nums[0][5 : 4], nums[0][3 : 2], nums[0][1 : 0], ans[0][5 : 0]);
		$display("b) C(1,8) %x (16) = %b", nums[1], ans[1][7 : 0]);
		$display("c) C(2,6) %d%d%d (4) = %b", nums[2][5 : 4], nums[2][3 : 2], nums[2][1 : 0], ans[2][5 : 0]);
		$display("d) C(2,10) %o (8) = %b", nums[3], ans[3][9 : 0]);
		$display("e) C(2,8) %x (16) = %b", nums[4], ans[4][7 : 0]);
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