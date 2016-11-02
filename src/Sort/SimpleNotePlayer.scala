package Sort

import javax.sound.midi._

/**
  * Plays notes. Takes integers as notes for a specific time. 0 represents C
  * Note: This file was created by the personnel at Lund University
  */
object SimpleNotePlayer {
  val channel: Option[Array[MidiChannel]] = try {
    val midiSynth = MidiSystem.getSynthesizer();
    midiSynth.open();

    val i = midiSynth.getDefaultSoundbank().getInstruments()

    /*
    0 for piano
    24 for nylon string guitar
    29 for distortion
    80 - 87 lead
     */
    midiSynth.loadInstrument(i.apply(80)); //load an instrument


    Some(midiSynth.getChannels());

  } catch {
    case e: MidiUnavailableException => println(e); None
  }

  /**
    * Plays the note and let it ring for the specified time
    */
  def play(note: Int, time: Int): Unit = {
    channel.getOrElse {
      println("Could not load MIDI"); System.exit(1); Array[MidiChannel]()
    }(0).noteOn(note + 24, time)
  }

  /**
    * Stops all notes playing
    */
  def stop() = channel.getOrElse {
    println("Could not load MIDI"); System.exit(1); Array[MidiChannel]()
  }(0).allNotesOff
}

