/*
	Guia_0905.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`include "clock.v"

module Guia_0905;
	wire clock;
	
	clock clk ( clock );
	
	wire p;
	
	pulse pulse ( p, clock );
	
	initial begin
		$dumpfile ( "Guia_0905.vcd" );
		$dumpvars ( 1, clock, p );
		#376 $finish;
	end
endmodule

module pulse ( signal, clock );
	input clock;
	output signal;	
	reg signal;
	
	always @ ( posedge clock )
	begin
		signal = 1'b1;
		#4 signal = 1'b0;
		#4 signal = 1'b1;
		#4 signal = 1'b0;
	end
endmodule
