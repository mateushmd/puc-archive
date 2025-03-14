/*
	Guia_0907.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`include "clock.v"

module Guia_0907;
	wire clock;
	
	clock clk ( clock );
	
	wire p;
	
	pulse pulse ( p, clock );
	
	initial begin
		$dumpfile ( "Guia_0907.vcd" );
		$dumpvars ( 1, clock, p );
		#376 $finish;
	end
endmodule

module pulse ( signal, clock );
	input clock;
	output signal;	
	reg signal;
	
	initial 
	begin
		signal = 1'b0;
	end
	
	always @ ( posedge clock )
	begin
		signal = ~signal;
		#8 signal = ~signal;
	end
endmodule
