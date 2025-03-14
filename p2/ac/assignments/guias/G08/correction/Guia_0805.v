/*
	Guia_0805.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0805;
	reg [5 : 0] x;
	wire [6 : 0] complement;
	wire [5 : 0] carry;
	
	fcomplement fc0(carry[0], complement[0], x[0], 1'b1);
	fcomplement fc1(carry[1], complement[1], x[1], carry[0]);
	fcomplement fc2(carry[2], complement[2], x[2], carry[1]);
	fcomplement fc3(carry[3], complement[3], x[3], carry[2]);
	fcomplement fc4(carry[4], complement[4], x[4], carry[3]);
	fcomplement fc5(carry[5], complement[5], x[5], carry[4]);
	fcomplement fc6(carry[5], complement[6], 1'b1, 1'b1);
	
	initial 
	begin: main
		integer i;
	
		x = 6'b000000;
	
		$monitor("%b -> %b", x, complement);
		
		for(i = 0; i < 64; i++)begin
			#1
			
			if((i + 1) % 2 == 0) begin
				x[0] = ~x[0];
			end
			
			if((i + 1) % 4 == 0) begin
				x[1] = ~x[1];
			end
			
			if((i + 1) % 8 == 0) begin
				x[2] = ~x[2];
			end
			
			if((i + 1) % 16 == 0) begin
				x[3] = ~x[3];
			end
			
			if((i + 1) % 32 == 0) begin
				x[4] = ~x[4];
			end
			
			if((i + 1) % 64 == 0) begin
				x[5] = ~x[5];
			end
		end
	end
endmodule

module fcomplement (output s1, s0, input x, cIn);
	wire nx;
	
	not NOT1(nx, x);
	xor XOR1(s0, nx, cIn);
	and AND1(s1, nx, cIn);
endmodule